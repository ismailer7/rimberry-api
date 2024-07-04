import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  constructor(private http: HttpClient) { }

  add(receipt: any) {
    return this.http.post<any>('http://localhost:8080/api/v1/receipt/add', receipt)
    .pipe(map(response => {
      return response;
    }))
  }

  all() {
    return this.http.get<any>('http://localhost:8080/api/v1/receipt/all')
    .pipe(map(response => {
      return response;
    }))
  }

}
