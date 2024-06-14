import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReceiptRoutingModule } from './receipt-routing.module';
import { ReceiptListComponent } from './receipt-list/receipt-list.component';
import { UIModule } from 'src/app/shared/ui/ui.module';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    ReceiptListComponent
  ],
  imports: [
    CommonModule,
    ReceiptRoutingModule,
    NgbDropdownModule,
    UIModule,
  ]
})
export class ReceiptModule { }
