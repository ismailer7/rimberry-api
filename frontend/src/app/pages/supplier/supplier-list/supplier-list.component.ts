import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { SupplierService } from 'src/app/core/services/supplier.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-supplier-list',
  templateUrl: './supplier-list.component.html',
  styleUrls: ['./supplier-list.component.scss']
})
export class SupplierListComponent implements OnInit {

  suppliers: any[];
  formData: FormGroup;
  editFormData: FormGroup;
  // page
  currentPage: number
  totalPages: number
  totalElements: number
  hasNext: boolean
  hasPrevious: boolean
  pages: any

  breadCrumbItems: Array<{}>;

  currentSupplier: any

  constructor(private modalService: NgbModal, private formBuilder: FormBuilder, private supplierService: SupplierService, private toastrService: ToastrService) { 

  }

  ngOnInit(): void {
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Suppliers', active: true }];
    this.getByPage(1);

    this.formData = this.formBuilder.group({
      fullname: ['', [Validators.required]],
      source: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
      address: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      cin: ['', [Validators.required, Validators.minLength(8)]],
      rib: ['', [Validators.required, Validators.minLength(16), Validators.maxLength(24)]],
    })
    
    this.editFormData = this.formBuilder.group({
      fullname: ['', [Validators.required]],
      source: ['', [Validators.required]],
      email: ['', Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')],
      address: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      cin: ['', [Validators.required]],
      rib: ['', [Validators.required]]
    })

  }

  get form() {
    return this.formData.controls;
  }

  get editForm() {
    return this.editFormData.controls;
  }

  addSupplier() {
    console.log(this.formData.controls)
    if(this.formData.valid) {
      Swal.fire({
        title: 'Saving Supplier..'
      });
      Swal.showLoading();
      const supplier = {
        "name": this.formData.get('fullname').value,
        "email": this.formData.get('email').value,
        "address": this.formData.get('address').value,
        "source": this.formData.get('source').value,
        "phone": this.formData.get('phone').value,
        "cin": this.formData.get('cin').value,
        "rib": this.formData.get('rib').value,
      };
      console.log(supplier);
      this.supplierService.add(supplier)
        .subscribe(result => {
          this.modalService.dismissAll()
          setTimeout(()=> {
            Swal.close();
            console.log(result);
            Swal.fire('Added!', result.message, 'success').then(event => {
             this.formData.reset();
           })
           this.ngOnInit();
           }, 3000)
        })
    }
  }

  openModal(content: any, supplier?: any) {
    if(supplier != 'undefined') {
      this.currentSupplier = supplier;
    }
    const modalRef = this.modalService.open(content, {size: 'lg'});
  }

  delete(index: string) {
    console.log(index)
    Swal.fire({
      title: 'Deleting Supplier..'
    });
    Swal.showLoading();
    this.supplierService.delete(index)
      .subscribe(result => {
        setTimeout(()=> {
          Swal.close();
          console.log(result);
          Swal.fire('Deleted!', result.message, 'success').then(event => {
           this.formData.reset();
         })
         this.ngOnInit();
         }, 3000)
    })
  }

  getByPage(page: any) {
    this.supplierService.getByPage(page)
    .subscribe(result => {
      this.suppliers = result['content'];
      this.currentPage = result['currentPage'];
      this.totalPages = result['totalPages'];
      this.totalElements = result['totalElements'];
      this.hasNext = result['hasNext'];
      this.hasPrevious = result['hasPrevious'];
      this.pages = Array(this.totalPages).fill(1).map((x,i)=>i+1)
      console.log(this.suppliers);
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

  editSupplier() {
    const supplier = {
      "id": this.currentSupplier['id'],
      "name": this.editFormData.get('fullname').value,
      "email": this.editFormData.get('email').value,
      "address": this.editFormData.get('address').value,
      "source": this.editFormData.get('source').value,
      "phone": this.editFormData.get('phone').value,
      "cin": this.editFormData.get('cin').value,
      "rib": this.editFormData.get('rib').value,
    };

    this.supplierService.edit(supplier)
      .subscribe(result => {
        Swal.fire('Successfully Edited!', result.message, 'success');
        this.ngOnInit();
      })
    this.modalService.dismissAll();
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
