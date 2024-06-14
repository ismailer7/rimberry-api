import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FactoryRoutingModule } from './factory-routing.module';
import { FactoryListComponent } from './factory-list/factory-list.component';
import { NgbDropdownModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UIModule } from 'src/app/shared/ui/ui.module';


@NgModule({
  declarations: [
    FactoryListComponent
  ],
  imports: [
    CommonModule,
    FactoryRoutingModule,
    UIModule,
    NgbTooltipModule,
    NgbDropdownModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class FactoryModule { }
