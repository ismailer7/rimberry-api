import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/core/services/user.service';

import Swal from 'sweetalert2';
import { User } from './user.model';
import { SidebarComponent } from 'src/app/layouts/sidebar/sidebar.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [SidebarComponent],
})
export class UserListComponent implements OnInit {
  constructor(private sidebarComponent: SidebarComponent, private modalService: NgbModal, private userService: UserService, private toastrService: ToastrService, private formBuilder: FormBuilder) { }

  users: [];
  formData: FormGroup;
  formData2: FormGroup;
  submitted = false;

  breadCrumbItems: Array<{}>;

  term: any;

  // page
  currentPage: number
  totalPages: number
  totalElements: number
  hasNext: boolean
  hasPrevious: boolean
  pages: any

  userToAdd: User;

  currentUserToBeEdited: any;

  // matching password

  passwordMatched: any

  currentUser: any

  ngOnInit(): void {
    this.passwordMatched = "false";
    this.getByPage(1);
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Employee', active: true }];
    this.formData = this.formBuilder.group({
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      password: ['', [Validators.required]],
      repeatPassword: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      gender: ['', [Validators.required]],
      permissions: ['', [Validators.required]],
      status: ['', [Validators.required]]
    }, {validators: this.password});

    this.formData2 = this.formBuilder.group({
      firstname: ['', []],
      lastname: ['', []],
      email: ['', [Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      permissions: ['', []],
    });
    this.currentPage = 1;
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
  }

  password(formGroup: FormGroup) {
    const { value: password } = formGroup.get('password');
    const { value: repeatPassword } = formGroup.get('repeatPassword');
    if(password === repeatPassword) {
      this.passwordMatched = true;
      return null;
    } else {
      this.passwordMatched = false;
      return { passwordNotMatch: true };
    }
  }

  checkPassword() {
    let password = this.formData.get('password').value
    let confirmPassword = this.formData.get('repeatPassword').value
    password === confirmPassword ? this.passwordMatched = "true" : this.passwordMatched = "false";
    console.log(password)
    console.log(confirmPassword)
    console.log('password matched: ' + this.passwordMatched)
  }

  hello(): any {
    console.log('hello from modal.')
  }
  
  getByPage(page: any) {
    console.log('Get Registered Members by page');
    this.userService.getByPage(page)
    .subscribe(result => {
      console.log('result by page: ', result);
      this.users = result['content'];
      this.currentPage = result['currentPage'];
      this.totalPages = result['totalPages'];
      this.totalElements = result['totalElements'];
      this.hasNext = result['hasNext'];
      this.hasPrevious = result['hasPrevious'];
      this.pages = Array(this.totalPages).fill(1).map((x,i)=>i+1)
      console.log("pages: ", this.pages)
    })
  }

  previous() {
    console.log('previous element', this.currentPage - 1)
    this.getByPage(this.currentPage - 1)
  }

  next() {
    console.log('next element', this.currentPage + 1)
    this.getByPage(this.currentPage + 1)
  }

  getAll() {
    console.log('Get All Registered Users..')
    this.userService.getAll()
    .subscribe(result => {
      this.users = result;
      console.log(result);
    })
  }

  getRoles(roles) {
    return roles.split("|");
  }

  getStatus(user) {
    return user.isActive ? "active" : "inactive";
  }

  isLoggedIn(user) {
    return user.isLogged ? "online" : "offline";
  }

  
  openModal(content: any, user?: any) {
    if(user != 'undefined') {
      // edit action

     this.currentUserToBeEdited = user;
     console.log(this.currentUserToBeEdited);
    }
    const modalRef = this.modalService.open(content, {size: 'lg'});
  }

  get form() {
    return this.formData.controls;
  }

  saveUser() {
    console.log('"save user action.')
    const currentDate = new Date();
    console.log(this.formData)
    //console.log(this.formData.errors.passwordNotMatch)
    if(this.formData.valid) {
      this.submitted = true;
      const permissions = this.formData.get('permissions').value
      let roles = "";
      for(let i = 0; i < permissions.length; i++) {
        roles += permissions[i].replace(/:.*/, "") + ",";
      }
      if(roles.endsWith(",")) roles = roles.substring(0, roles.length - 1);
      const userToAdd = {
        'firstName': this.formData.get('firstname').value,
        'lastName':this.formData.get('lastname').value,
        'email':this.formData.get('email').value,
        'isActive': this.formData.get('status').value === 'ACTIVE' ? true : false,
        'password': this.formData.get('password').value,
        'gender': this.formData.get('gender').value,
        'roles': roles
      };
      console.log(userToAdd);
      this.userService.add(userToAdd)
      .subscribe(result => {
        Swal.fire('Added!', result.message, 'success');
        this.ngOnInit();
      })
    }
    
      this.modalService.dismissAll()
  }

  edit() {
    console.log(this.formData2)
    const permissions = this.formData2.get('permissions').value
      let roles = "";
      for(let i = 0; i < permissions.length; i++) {
        roles += permissions[i].replace(/:.*/, "") + ",";
      }
      if(roles.endsWith(",")) roles = roles.substring(0, roles.length - 1);
      const userToEdit = {
        'id': this.currentUserToBeEdited['id'],
        'firstName': this.formData2.get('firstname') != null ? this.formData2.get('firstname').value  : null,
        'lastName':this.formData2.get('lastname') != null ? this.formData2.get('lastname').value : null,
        'email':this.formData2.get('email') != null ? this.formData2.get('email').value : null,
        'password': this.formData2.get('password') != null ? this.formData2.get('password').value : null,
        'gender': this.formData2.get('gender') != null ? this.formData2.get('gender').value : null,
        'roles': roles
      };
      this.userService.edit(userToEdit)
      .subscribe(result => {
        //let cu = JSON.parse(sessionStorage.getItem('currentUser'));
        //cu['roles'] = roles.replaceAll(',', '|').replace('0', 'RECEPTION').replace('1', 'ADMIN').replace('2', 'SUPERVISOR').replace('3', 'HR');
        //console.log('all roles' + JSON.stringify(cu))
        //sessionStorage.setItem('currentUser', JSON.stringify(cu));
        if(this.currentUser['userId'] == this.currentUserToBeEdited['id']) {
          sessionStorage.removeItem('currentUser');
          Swal.fire('Successfully Edited!', result.message + ", You will be logged out!!", 'success').then(()=> {
            window.location.reload();
          })
        } else {
          this.userService.logout(this.currentUserToBeEdited['id']);
          Swal.fire('Successfully Edited!', result.message, 'success').then(()=> {
            // pass
          })
        }
        this.ngOnInit();
        
      })
      this.modalService.dismissAll()
  }

  confirm(index: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Your are Trying to Delete User with ID : ' + index + ' !',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#34c38f',
      cancelButtonColor: '#f46a6a',
      confirmButtonText: 'Yes, delete it!'
    }).then(result => {
      if (result.value) {
        Swal.fire('Deleted!', 'User has been Deleted!', 'success');
        this.userService.delete(index)
        .subscribe(result => {
          console.log(result);
          this.ngOnInit();
        })
      }
    });
  }

  search(event: any) {
    console.log(event.target.value)
    if(event.target.value == "") {
      console.log("empty lookup")
      this.getByPage(this.currentPage);
      return;
    }
    this.userService.lookup(event.target.value)
    .subscribe(result => {
      console.log(result);
      this.users = result;
    })
  }

  async onCopy(text: string) {
    console.log(text + 'copied!!')
    try {
      await navigator.clipboard.writeText(text);
      // Optional: Provide feedback or perform additional actions upon successful copy
      this.toastrService.info(text, 'Copied to Clipboard!');
    } catch (error) {
      console.error("Failed to copy to clipboard:", error);
      // Optional: Handle and display the error to the user
      this.toastrService.error(text, 'Not Copied!');
    }
  }

}
