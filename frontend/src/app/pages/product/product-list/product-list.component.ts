import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { map } from 'rxjs/operators';
import { ProductService } from 'src/app/core/services/product.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  productData: any[];
  formData: FormGroup;
  formEditData: FormGroup;
  // page
  currentPage: number
  totalPages: number
  totalElements: number
  hasNext: boolean
  hasPrevious: boolean
  pages: any

  breadCrumbItems: Array<{}>;
  currentProduct: any

  constructor(private modalService: NgbModal, private formBuilder: FormBuilder, private productService: ProductService, private toastrService: ToastrService) { 
    
  }

  ngOnInit(): void {
    this.getByPage(1);
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Products', active: true }];

    this.formData = this.formBuilder.group({
      name: ['', [Validators.required]],
      type: ['', [Validators.required]]
    })

    this.formEditData = this.formBuilder.group({
      name: ['', [Validators.required]],
      type: ['', [Validators.required]]
    })

  }

  get form() {
    return this.formData.controls;
  }

  get editForm() {
    return this.formEditData.controls;
  }

  addProduct() {
    console.log(this.formData);
    if(this.formData.valid) {
      const product = {
        "name": this.formData.get('name').value,
        "type": this.formData.get('type').value,
      };
      this.productService.add(product).subscribe(result => {
        console.log(result);
        this.ngOnInit();
        this.modalService.dismissAll();
        this.toastrService.success(result.message, 'Added!');
      })
    }
  }

  delete(index: string) {
    console.log(index)
    this.productService.delete(index)
      .subscribe(result => {
          console.log(result);
          this.ngOnInit();
          this.toastrService.success(result.message, 'Deleted!');
    })
  }

  openModal(content: any, product?: any) {
    if(product != 'undefined') {
      this.currentProduct = product;
    }
    const modalRef = this.modalService.open(content, {size: 'lg'});
  }

  getByPage(page: any) {
    this.productService.getByPage(page)
    .subscribe(result => {
      this.productData = result['content'];
      this.currentPage = result['currentPage'];
      this.totalPages = result['totalPages'];
      this.totalElements = result['totalElements'];
      this.hasNext = result['hasNext'];
      this.hasPrevious = result['hasPrevious'];
      this.pages = Array(this.totalPages).fill(1).map((x,i)=>i+1)
      console.log(this.productData);
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

  
  editProduct() {
    const product = {
      "id": this.currentProduct['id'],
      "name": this.formEditData.get('name').value,
      "type": this.formEditData.get('type').value,
    };

    this.productService.edit(product)
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
      this.productService.lookup(event.target.value)
    .subscribe(result => {
      console.log(result)
      this.productData = result
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
