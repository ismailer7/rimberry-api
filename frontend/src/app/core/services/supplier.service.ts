import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

  constructor(private http: HttpClient) { }

  getByPage(page: any) {
    return this.http.get<any>(`${environment.api}/supplier/suppliers?page=${page}`)
    .pipe(map(response => {
        return response;
    }));

  }

  delete(supplierId: string) {
    return this.http.delete<any>(`${environment.api}/supplier/delete/${supplierId}`)
    .pipe(map(response => {
        console.log('delete operation result : ' + response)
        return response;
    }));
  }

  add(supplier: any) {
    return this.http.post<any>(`${environment.api}/supplier/add`, supplier)
    .pipe(map(response => {
      return response;
    }))
  }

  lookup(text: string) {
    return this.http.get<any>(`${environment.api}/supplier/search?text=${text}`)
    .pipe(map(response => {
        return response;
    }))

  }

  edit(supplier: any) {
    return this.http.put<any>(`${environment.api}/supplier/edit`, supplier)
    .pipe(map(response => {
        console.log('Edit operation Response: ', response);
        return response;
    }))
  }

  all() {
    return this.http.get<any>(`${environment.api}/supplier/all`)
    .pipe(map(response => {
        return response;
    }))
  }

}
