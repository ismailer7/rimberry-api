import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestRoutingModule } from './test-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { UIModule } from 'src/app/shared/ui/ui.module';


@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    TestRoutingModule,
    ReactiveFormsModule,
    NgbTooltipModule,
    NgbDropdownModule,
    ReactiveFormsModule,
    FormsModule,
    UIModule,
  ]
})
export class TestModule { }
