import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagesRoutingModule } from './pages-routing.module';
import { TestComponent } from './test/test.component';
import { SimplebarAngularModule } from 'simplebar-angular';
import { UserModule } from './user/user.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FactoryModule } from './factory/factory.module';
import { SupplierModule } from './supplier/supplier.module';
import { ProductModule } from './product/product.module';
import { TestModule } from './test/test.module';
import { RawMaterialModule } from './raw-material/raw-material.module';
import { ReceiptModule } from './receipt/receipt.module';

@NgModule({
  declarations: [
    TestComponent,
  ],
  imports: [
    CommonModule,
    PagesRoutingModule,
    SimplebarAngularModule,
    UserModule,
    FactoryModule,
    SupplierModule,
    ProductModule,
    RawMaterialModule,
    TestModule,
    ReceiptModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class PagesModule { }
