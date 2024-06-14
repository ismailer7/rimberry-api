import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  constructor(private http: HttpClient) { }

  add(receipt: any) {
    return this.http.post<any>(`${environment.api}/receipt/add`, receipt)
    .pipe(map(response => {
      return response;
    }))
  }

  all() {
    return this.http.get<any>(`${environment.api}/receipt/all`)
    .pipe(map(response => {
      return response;
    }))
  }

}
