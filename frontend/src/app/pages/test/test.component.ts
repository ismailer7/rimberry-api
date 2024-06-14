import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {
  

  breadCrumbItems: Array<{}>;

  form: FormGroup;
  phoneData: FormGroup;

  constructor(private fb: FormBuilder, private toastrService: ToastrService) {
    this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Suppliers', active: true }];
    this.form = this.fb.group({
      formlist: this.fb.array([]),
    }),

    this.phoneData = this.fb.group({
      phoneValue: this.fb.array([]),
    });
   }

  ngOnInit(): void {
    // this.breadCrumbItems = [{ label: 'Rimberry' }, { label: 'Raw Material', active: true }];
  }

  formData(): FormArray {
    return this.form.get('formlist') as FormArray;
  }

  phonedata(): FormArray {
    return this.phoneData.get('phoneValue') as FormArray;
  }

  phone(): FormGroup {
    return this.fb.group({
      phonenumber: ''
    });
  }

  field(): FormGroup {
    return this.fb.group({
      name: '',
      email: '',
      subject: '',
      file: '',
      msg: '',
    });
  }

  /**
   * Add phone field in list
   */
  addPhone() {
    this.phonedata().push(this.phone());
  }

  /**
   * Remove field from form
   * @param i specified index to remove
   */
  removeField(i: number) {
      this.formData().removeAt(i);
      this.toastrService.success('Entry Deleted!', 'Deleted!');
  }

  /**
   * Delete phone field from list
   * @param i specified index
   */
  deletePhone(i: number) {
    this.phonedata().removeAt(i);
  }

  /**
   * Add field in form
   */
  addField() {
    this.formData().push(this.field());
  }

}
