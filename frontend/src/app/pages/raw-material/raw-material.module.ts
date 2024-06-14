import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RawMaterialRoutingModule } from './raw-material-routing.module';
import { RawMaterialReceptionComponent } from './raw-material-reception/raw-material-reception.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { UIModule } from 'src/app/shared/ui/ui.module';
import { ArchwizardModule } from 'angular-archwizard'


@NgModule({
  declarations: [
    RawMaterialReceptionComponent
  ],
  imports: [
    CommonModule,
    RawMaterialRoutingModule,
    ReactiveFormsModule,
    NgbTooltipModule,
    NgbDropdownModule,
    ArchwizardModule,
    ReactiveFormsModule,
    FormsModule,
    UIModule,
  ]
})
export class RawMaterialModule { }
