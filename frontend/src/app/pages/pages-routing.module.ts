import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TestComponent } from './test/test.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { SupplierListComponent } from './supplier/supplier-list/supplier-list.component';
import { RawMaterialReceptionComponent } from './raw-material/raw-material-reception/raw-material-reception.component';
import { FactoryListComponent } from './factory/factory-list/factory-list.component';
import { ReceiptListComponent } from './receipt/receipt-list/receipt-list.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'test', component: TestComponent},
  { path: 'userList', component: UserListComponent},
  { path: 'productList', component: ProductListComponent},
  { path: 'supplierList', component: SupplierListComponent},
  { path: 'rawMaterial', component: RawMaterialReceptionComponent},
  { path: 'factoryList', component: FactoryListComponent},
  { path: 'receiptList', component: ReceiptListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
