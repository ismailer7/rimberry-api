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

  form1: FormGroup;
  form2: FormGroup;

  date: string
  driver: string
  vrn: string
  selectedProductId: number
  selectedSupplierId: number
  selectedProduct: any
  productName: string
  selectedSupplier: any
  supplierName: string
  supplierAddress: string
  supplierPhone: string
  supplierRib: string


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
  te: number = 0;
  to: number = 0;
  tb: number = 0;
  palettes: Pallete[] = [];
  selectedType: string = null;
  grosses: number[] = [];
  productData: any;
  supplierData: any;

  isLoading: boolean = false;
  receiptData: any;


  // reception Info
  receptionPB: number = 0;
  receptionPP: number = 0;
  receptionTotalCaisses: number = 0;
  receptionTareCases: number = 0;
  receptionTare:number = 0;
  receptionNet:number = 0;
  totalPalletes:number = 0;

  constructor(private fb: FormBuilder, private toastrService: ToastrService, private supplierService: SupplierService,
    private productService: ProductService, private receiptService: ReceiptService, private modalService: NgbModal) {
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Raw Materials', active: true }];

    this.form1 = this.fb.group({
      date: ['', [Validators.required]],
      supplierId: ['', [Validators.required]],
      productId: ['', [Validators.required]],
      driver: ['', [Validators.required]],
      rnumber: ['', [Validators.required]]
    })




    this.form2 = this.fb.group({
      pp: [this.pp, [Validators.required]],
      pb: ['0', [Validators.required]],
      nc: ['0', [Validators.required]],
      typec: ['', [Validators.required]],
      total: ['0', [Validators.required]],
      tareCaisse: ['0', [Validators.required]],
      gmass: ['0', [Validators.required]],
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

  isForm1Valid() {
    console.log('form1: ' + this.form1.valid)
    return this.form1.valid;
  }

  submitForm1() {
    if(this.form1.valid) {
      this.date = this.form1.get('date').value;
      this.driver = this.form1.get('driver').value;
      this.vrn = this.form1.get('rnumber').value;
      this.selectedProductId = this.form1.get('productId').value
      this.selectedSupplierId = this.form1.get('supplierId').value
      this.supplierService.getById(this.selectedSupplierId)
      .subscribe(result => {
        this.selectedSupplier = JSON.stringify(result);
        console.log('supplier data' + JSON.stringify(result));
        this.supplierName = result['name']
        this.supplierAddress = result['address']
        this.supplierPhone = result['phone']
        this.supplierRib = result['rib']
      })
      this.productService.getById(this.selectedProductId)
      .subscribe(result => {
        //console.log('result suplier' + JSON.stringify(result))
        this.selectedProduct = JSON.stringify(result);
        this.productName = result['name']
        //console.log('product NAme: ' + this.productName)
      })
      console.log('selected product' + this.selectedProduct)
      console.log('selected supplier' + this.selectedSupplier)
    }
  }

  isForm2Valid() {
    console.log('form2: ' + this.form2.valid)
    return this.form2.valid;
  }

  ngOnInit(): void {
    //this.nmass = this.gmass - this.tare;
    this.form2.get('net').setValue(this.netmass);
    this.form2.get('gross').setValue(this.grossMass);
    this.form2.get('total').setValue(this.totalNC);
    this.tare = this.grossMass + this.pp;
    this.netmass = this.tare - this.pb;
    if(this.netmass < 0) this.netmass *= -1;
    this.form2.get('net').setValue(this.netmass)
    console.log('netmass: ' + this.netmass);
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
    return this.form2.get('formlist') as FormArray;
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
    console.log('types: ', this.types)

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

    if(this.types[index] == 0.70) {
      this.te = this.te + this.ncs[index]
    }

    if(this.types[index] == 1.20) {
      this.to = this.to + this.ncs[index]
    }

    if(this.types[index] == 1.10) {
      this.tb = this.tb + this.ncs[index]
    }

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
      'id': this.nlotGenerate(),
      'pb': this.pb,
      'pp': this.pp,
      'totalCases': this.totalNC,
      'te': this.te,
      'to': this.to,
      'tb': this.tb,
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
      this.form2.reset();
      this.form1.reset();
      this.palettes.push(pallete);
      console.log(this.palettes);
      this.pp = 0;
      this.pb = 0;
      this.totalNC = 0;
      this.grossMass = 0;
      this.tare = 0;
      this.netmass = 0;
     })
     }, 3000)
  }

  /*week(year,month,day) {
    function serial(days) { return 86400000*days; }
    function dateserial(year,month,day) { return (new Date(year,month-1,day).valueOf()); }
    function weekday(date) { return (new Date(date)).getDay()+1; }
    function yearserial(date) { return (new Date(date)).getFullYear(); }
    var date = year instanceof Date ? year.valueOf() : typeof year === "string" ? new Date(year).valueOf() : dateserial(year,month,day),
        date2 = dateserial(yearserial(date - serial(weekday(date-serial(1))) + serial(4)),1,3);
    return ~~((date - date2 + serial(weekday(date2) + 5))/ serial(7));
}*/

  nlotGenerate() {
    const now = new Date()
    const day = now.getDay() + 1
    const month = now.getMonth()
    const year = now.getFullYear()

    const nlot = 23 + '/' + 3 + '/' + 2024 + '/' + 12;
    return nlot;
  }

  onSubmit() {
    console.log('on submit ..')
    console.log(this.form1.controls)

    Swal.fire({
      title: 'Saving Receipt..'
    });
    Swal.showLoading();
    const receipt = {
        'date': this.date,
        'supplierId': Number(this.selectedSupplierId),
        'productId': Number(this.selectedProductId),
        'driver': this.driver,
        'pb': Number(this.receptionPB),
        'tp': Number(this.totalPalletes),
        'pp': Number(this.receptionPP),
        'tc': Number(this.receptionTotalCaisses),
        'tarep': Number(this.receptionTareCases),
        'tare': Number(this.receptionTare),
        'tn': Number(this.receptionNet)
    }
      console.log(receipt);
      this.receiptService.add(receipt)
      .subscribe(result => {
        setTimeout(()=> {
         Swal.close();
         /*  this.toastrService.success('Receipt Added!', 'Added!'); */
         Swal.fire('Added!', result.message, 'success').then(event => {
          this.form2.reset();
        })
        }, 3000)
      })

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


  delete(index: any) {
    Swal.fire({
      title: 'Deleting Pallete..'
    });
    Swal.showLoading();
    setTimeout(()=> {
      Swal.close();
      this.palettes.splice(index, 1);
      /*  this.toastrService.success('Receipt Added!', 'Added!'); */
      Swal.fire('Added!', 'Pallete deleted successfully', 'success').then(event => {
       // nothing
       this.ngOnInit()
     })
     }, 3000)
  }

  printPallete(index: any) {
    Swal.fire({
      title: 'Deleting Pallete..'
    });
    Swal.showLoading();
    setTimeout(()=> {
      Swal.close();
      this.palettes.splice(index, 1);
      /*  this.toastrService.success('Receipt Added!', 'Added!'); */
      Swal.fire('Added!', 'Pallete deleted successfully', 'success').then(event => {
       // nothing
       this.ngOnInit()
     })
     }, 3000)
  }

  calculateReceptionTotal() {
    this.receptionPB = 0
    this.receptionPP = 0
    this.receptionTotalCaisses = 0
    this.receptionTareCases = 0
    this.receptionTare = 0
    this.receptionNet = 0
    this.totalPalletes = this.palettes.length
    this.palettes.map(pallete => {
    this.receptionPB += pallete['pb']
    this.receptionPP += pallete['pb']
    this.receptionTotalCaisses += pallete['totalCases']
    this.receptionTareCases += pallete['tareCases']
    this.receptionTare += pallete['tare']
    this.receptionNet += pallete['net']
   })
  }

  clearFields() {
    console.log('clear fields..')
  }

}
