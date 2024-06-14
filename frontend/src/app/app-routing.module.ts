import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LayoutsComponent } from './layouts/layouts.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { FooterComponent } from './layouts/footer/footer.component';
const routes: Routes = [
  { path: 'account', loadChildren: () => import('./account/account.module').then(m => m.AccountModule) },
  // tslint:disable-next-line: max-line-length
   //{ path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  //{ path: 'footer', component: FooterComponent, canActivate: [AuthGuard] },
  { path: '', component: LayoutsComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule), canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
