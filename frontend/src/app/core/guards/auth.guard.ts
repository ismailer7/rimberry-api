import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthfakeauthenticationService } from '../services/authfake.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authFackservice: AuthfakeauthenticationService
) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authFackservice.currentUserValue;
      console.log('AuthGuard Running..');
      if (currentUser) {
        // logged in so return true
        console.log('Current Route {}', route);
        return true;
      }
       // not logged in so redirect to login page with the return url
       this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url } });
       return false;
  }
  
}
