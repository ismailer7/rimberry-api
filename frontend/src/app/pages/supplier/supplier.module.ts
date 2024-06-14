import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupplierRoutingModule } from './supplier-routing.module';
import { SupplierListComponent } from './supplier-list/supplier-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { UIModule } from 'src/app/shared/ui/ui.module';


@NgModule({
  declarations: [
    SupplierListComponent
  ],
  imports: [
    CommonModule,
    SupplierRoutingModule,
    ReactiveFormsModule,
    NgbTooltipModule,
    NgbDropdownModule,
    ReactiveFormsModule,
    FormsModule,
    UIModule,
  ]
})
export class SupplierModule { }
