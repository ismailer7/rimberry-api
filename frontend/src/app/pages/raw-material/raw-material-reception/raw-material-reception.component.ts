import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/core/services/product.service';
import { ReceiptService } from 'src/app/core/services/receipt.service';
import { SupplierService } from 'src/app/core/services/supplier.service';
import Swal from 'sweetalert2';
import { Pallete } from './Pallete.module';

@Component({
  selector: 'app-raw-material-reception',
  templateUrl: './raw-material-reception.component.html',
  styleUrls: ['./raw-material-reception.component.scss']
})
export class RawMaterialReceptionComponent implements OnInit {


  breadCrumbItems: Array<{}>;

  form: FormGroup;

  gmass: number = 0;
  tare: number = 0;
  nmass: number;

  grossMass: number = 0;



  netmass: number = 0;

  totalNC: number;
  pp: number = 0;
  pb: number = 0;
  tareCaisses: number[] = [];
  ncs : number[] = [];
  types: number[] = [];
  palettes: Pallete[] = [];
  selectedType: string = null;
  grosses: number[] = [];
  productData: any;
  supplierData: any;

  isLoading: boolean = false;
  receiptData: any;

  constructor(private fb: FormBuilder, private toastrService: ToastrService, private supplierService: SupplierService, 
    private productService: ProductService, private receiptService: ReceiptService, private modalService: NgbModal) { 
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Raw Materials', active: true }];
    this.form = this.fb.group({
      date: ['', Validators.required],
      supplierId: ['', [Validators.required]],
      productId: ['', [Validators.required]],
      pp: [this.pp, [Validators.required]],
      pb: ['0', [Validators.required]],
      gmass: ['0', [Validators.required]],
      tareCaisse: ['0', [Validators.required]],
      tare: ['0', [Validators.required]],
      nmass: ['', [Validators.required]],
      total: ['0', [Validators.required]],
      gross: ['0', [Validators.required]],
      net: ['0', [Validators.required]],
      formlist: this.fb.array([])
    }),

    this.totalNC = 0;

    this.supplierService.all()
    .subscribe(result => {
      console.log(result)
      this.supplierData = result
    })

    this.productService.all()
    .subscribe(result => {
      console.log(result)
      this.productData = result
    })

  }

  ngOnInit(): void {
    this.nmass = this.gmass - this.tare;
    this.form.get('nmass').setValue(this.nmass);
    this.form.get('net').setValue(this.netmass);
    this.form.get('gross').setValue(this.grossMass);
    this.form.get('total').setValue(this.totalNC);
    this.tare = this.grossMass + this.pp;
    this.netmass = this.tare - this.pb;
    if(this.netmass < 0) this.netmass *= -1;
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

  formData(): FormArray {
    return this.form.get('formlist') as FormArray;
  }


  phone(): FormGroup {
    return this.fb.group({
      phonenumber: ''
    });
  }

  field(): FormGroup {
    return this.fb.group({
      nc: '',
      typec: '',
    });
  }

  /**
   * Remove field from form
   * @param i specified index to remove
   */
  removeField(i: number) {
      this.formData().removeAt(i);
      this.ncs.splice(i, 1);
      this.types.splice(i, 1);
      this.grosses.splice(i, 1);
      if(this.ncs.length === 0) {
        this.totalNC = 0;
      } else {
        this.totalNC = this.ncs.reduce((nc1, nc2)=>{
          return nc1 + nc2;
        })  
      } 

      if(this.grosses.length !== 0) {
        this.grossMass = this.grosses.reduce((g1, g2)=>{
          return g1 + g2;
        })  
      } else this.grossMass = 0;
      this.toastrService.success('Entry Deleted!', 'Deleted!');
      console.log(this.ncs)
      this.ngOnInit();
  }


  /**
   * Add field in form
   */
  addField() {
    this.formData().push(this.field());
  }

  savePP(event: any) {
    this.pp = Number(event.target.value);
    this.ngOnInit();
  }

  onNCWrite(event: any, index: any) {
    this.ncs[index] = Number(event.target.value);
    console.log(this.ncs);
    if(this.types[index] == null) {
      this.types[index] = 0;
    }
    this.grosses[index] = this.ncs[index] * this.types[index];
    console.log('grosses: ', this.grosses)
    this.totalNC = this.ncs.reduce((nc1, nc2)=>{
      return nc1 + nc2;
    })

    this.grossMass = this.grosses.reduce((g1, g2)=>{
      return g1 + g2;
    })
    this.ngOnInit();
  }

  selectType(event: any, index: any) {
    console.log('testtt')
    this.types[index] = Number(event.target.value);
    this.grosses[index] = this.types[index] * this.ncs[index];
    console.log('grosses: ', this.grosses)
    this.grossMass = this.grosses.reduce((g1, g2)=>{
      return g1 + g2;
    }) 
    this.tare = this.grossMass + this.pp;
    this.ngOnInit();
  }

  savePalettePoidsBrute(event: any) {
    this.pb = Number(event.target.value)
    this.ngOnInit();
  }

  saveGMMASS(event: any) {
    this.gmass = Number(event.target.value)
    this.ngOnInit();
  }

  savePalettePoids(event: any) {
    this.pp = Number(event.target.value);
    this.ngOnInit();
  }

  get suppliers() {
    return this.supplierData
  }

  get products() {
    return this.productData;
  }

  addPallete() {
    const pallete = {
      'id': 111,
      'pb': this.pb,
      'pp': this.pp,
      'totalCases': this.totalNC,
      'tareCases': this.grossMass,
      'tare': this.tare,
      'net': this.netmass
    }
    Swal.fire({
      title: 'Adding Pallete N: ' + Number(this.palettes.length+1)
    });
    Swal.showLoading();
    setTimeout(()=> {
      Swal.close();
      /*  this.toastrService.success('Receipt Added!', 'Added!'); */
      Swal.fire('Added!', 'Pallete Added Successfully!', 'success').then(event => {
      this.form.reset();
      this.palettes.push(pallete);
      console.log(this.palettes);
      this.form.reset();
     })
     }, 3000)
  }

  onSubmit() {
    console.log('on submit ..')
    console.log(this.form.controls)
    if(this.form.valid) {
      Swal.fire({
        title: 'Saving Receipt..'
      });
      Swal.showLoading();
      const receipt = {
        'date': this.form.get('date').value,
        'supplierId': Number(this.form.get('supplierId').value),
        'productId': Number(this.form.get('productId').value),
        'grossMass1': Number(this.form.get('gmass').value),
        'netMass1': Number(this.form.get('nmass').value),
        'tare': Number(this.form.get('tare').value),
        'ncs': this.getNcs(),
        'total': Number(this.form.get('total').value),
        'grossMass2': Number(this.form.get('gross').value),
        'netMass2': Number(this.form.get('net').value)
      }
      console.log(receipt);
      this.receiptService.add(receipt)
      .subscribe(result => {
        setTimeout(()=> {
         Swal.close();
         /*  this.toastrService.success('Receipt Added!', 'Added!'); */
         Swal.fire('Added!', result.message, 'success').then(event => {
          this.form.reset();
        })
        }, 3000)
      })
    }
  }

  getNcs() {
    let ncVal = '';
    for (let index = 0; index < this.ncs.length; index++) {
      ncVal += this.ncs[index] + ':' + this.types[index] + '|';
    }
    return ncVal;
  }

  receipts() {
    this.receiptService.all()
    .subscribe(result => {
      this.receiptData = result;
      console.log(this.receiptData);
    })
  }

  print(element: string) {
    const printContents = document.getElementById(element).innerHTML;
     const originalContents = document.body.innerHTML;
     document.body.innerHTML = printContents;
     window.print();
     document.body.innerHTML = originalContents;
  }

}
