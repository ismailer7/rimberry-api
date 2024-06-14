import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { FactoryService } from 'src/app/core/services/factory.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-factory-list',
  templateUrl: './factory-list.component.html',
  styleUrls: ['./factory-list.component.scss']
})
export class FactoryListComponent implements OnInit {

  
  factoryData: any[];

  htmlContent: any;
  formData: FormGroup;
  editFormData: FormGroup;
  // page
  currentPage: number
  totalPages: number
  totalElements: number
  hasNext: boolean
  hasPrevious: boolean
  pages: any

  // saveButtonStatus
  saveButtonStatus: boolean

  currentFactory: any;

  breadCrumbItems: Array<{}>;

  constructor(private toastrService: ToastrService, private factoryService: FactoryService, private formBuilder: FormBuilder, private modalService: NgbModal) { 
    this.htmlContent = "<table class=\"table project-list-table table-nowrap align-middle table-borderless\"> <thead class=\"table-light\"> <tr> <th scope=\"col\" style=\"width: 100px\">#<\/th> <th scope=\"col\">Fullname<\/th> <th scope=\"col\">phone<\/th> <th scope=\"col\">Email<\/th> <th scope=\"col\">Location<\/th> <\/tr> <\/thead> <tbody> <tr *ngFor=\"let factory of factoryData; let i = index\"> <td> <img src=\"..\/..\/..\/..\/assets\/images\/owner\/owner.png\" alt class=\"avatar-sm\" \/> <\/td> <td> <h5 class=\"text-truncate font-size-14\"> <a href=\"javascript: void(0);\" class=\"text-dark\">${ownerName}<\/a> <\/h5> <\/td> <td> <h5 class=\"text-truncate font-size-14\"> ${ownerPhone} </h5><\/td> <td> <h5 class=\"text-truncate font-size-14\"> ${ownerEmail} </h5> <\/td> <td> <h5 class=\"text-truncate font-size-14\"> ${location} </h5> <\/td> <\/tr> <\/tbody> <\/table>";
  } 

  get form() {
    return this.formData.controls;
  }

  get editForm() {
    return this.editFormData.controls;
  }

  get saveButtonStatic() {
    return this.formData.errors != null ? true : false;
  }

  ngOnInit(): void {
    this.getByPage(1);
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Factories', active: true }];
    this.formData = this.formBuilder.group({
      name: ['', [Validators.required]],
      location: ['', [Validators.required]],
      status: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      fullname: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      ownerlocation: ['', [Validators.required]],
    })

    this.editFormData = this.formBuilder.group({
      name: ['', [Validators.required]],
      location: ['', [Validators.required]],
      status: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      fullname: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      ownerlocation: ['', [Validators.required]]
    })

  }

  getByPage(page: any) {
    this.factoryService.getByPage(page)
    .subscribe(result => {
      this.factoryData = result['content'];
      this.currentPage = result['currentPage'];
      this.totalPages = result['totalPages'];
      this.totalElements = result['totalElements'];
      this.hasNext = result['hasNext'];
      this.hasPrevious = result['hasPrevious'];
      this.pages = Array(this.totalPages).fill(1).map((x,i)=>i+1)
      console.log(result);
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

  delete(index: string) {
    console.log(index)
    this.factoryService.delete(index)
      .subscribe(result => {
          console.log(result);
          this.ngOnInit();
          this.toastrService.success(result.message, 'Deleted!');
    })
  }

  html(owner: any) {
    return "<table class=\"table project-list-table table-nowrap align-middle table-borderless\"> <thead class=\"table-light\"> <tr> <th scope=\"col\" style=\"width: 100px\">#<\/th> <th scope=\"col\">Fullname<\/th> <th scope=\"col\">phone<\/th> <th scope=\"col\">Email<\/th> <th scope=\"col\">Location<\/th> <\/tr> <\/thead> <tbody> <tr *ngFor=\"let factory of factoryData; let i = index\"> <td> <img src=\"..\/..\/..\/..\/assets\/images\/owner\/owner.png\" alt class=\"avatar-sm\" \/> <\/td> <td> <h5 class=\"text-truncate font-size-14\"> <a href=\"javascript: void(0);\" class=\"text-dark\">"
      + owner.fullname + "<\/a> <\/h5> <\/td> <td> <h5 class=\"text-truncate font-size-14\">"
      + owner.phone + "</h5><\/td> <td> <h5 class=\"text-truncate font-size-14\">"
      + owner.email + "</h5> <\/td> <td> <h5 class=\"text-truncate font-size-14\">"
      + owner.location + "</h5> <\/td> <\/tr> <\/tbody> <\/table>";
  }

  owner(owner: any) {
    Swal.fire({
      title: 'Factory Owner Information',
      text: 'Your are Trying to Delete User with ID : ' + ' !',
      html: this.html(owner),
      width: '80%',
      icon: 'info',
      confirmButtonText: 'Close'
    })
  }

  openModal(content: any, factory?: any) {
    if(factory != 'undefined') {
      this.currentFactory = factory;
    }
    const modalRef = this.modalService.open(content, {size: 'lg'});
  }

  addFactory() {
    console.log('adding factory..')
    console.log(this.formData.get('fullname').status)
    console.log(this.formData);
    if(this.formData.valid) {
      const factory = {
        "name": this.formData.get('fullname').value,
        "location": this.formData.get('location').value,
        "status": this.formData.get('status').value,
        "owner": {
          'fullname': this.formData.get('fullname').value,
          'email': this.formData.get('email').value,
          'location': this.formData.get('ownerlocation').value,
          'phone': this.formData.get('phone').value
        }
      };
      console.log(factory);
      this.factoryService.add(factory)
        .subscribe(result => {
          console.log(result);
          this.ngOnInit();
          this.modalService.dismissAll();
          this.toastrService.success(result.message, 'Added!');
        })
    }
  }


  editFactory() {
    const factory = {
      "id": this.currentFactory['id'],
      "name": this.editFormData.get('fullname').value,
      "location": this.editFormData.get('location').value,
      "status": this.editFormData.controls.status.value,
      "owner": {
        'fullname': this.editFormData.get('fullname').value,
        'email': this.editFormData.get('email').value,
        'location': this.editFormData.get('ownerlocation').value,
        'phone': this.editFormData.get('phone').value
      }
    };

    this.factoryService.edit(factory)
      .subscribe(result => {
        Swal.fire('Successfully Edited!', result.message, 'success');
        this.ngOnInit();
      })
    this.modalService.dismissAll();
  }

  lookup(event: any) {
    if(event.target.value == "") {
      this.getByPage(1)
    } else {
      this.factoryService.lookup(event.target.value)
    .subscribe(result => {
      console.log(result)
      this.factoryData = result
    })
    }
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
