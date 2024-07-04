import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

  constructor(private http: HttpClient) { }

  getByPage(page: any) {
    return this.http.get<any>(`http://localhost:8080/api/v1/supplier/suppliers?page=${page}`)
    .pipe(map(response => {
        return response;
    }));

  }

  getById(id: number) {
    return this.http.get<any>(`http://localhost:8080/api/v1/supplier?id=${id}`)
    .pipe(map(response => {
      return response;
    }))
  }

  delete(supplierId: string) {
    return this.http.delete<any>('http://localhost:8080/api/v1/supplier/delete/' + supplierId)
    .pipe(map(response => {
        console.log('delete operation result : ' + response)
        return response;
    }));
  }

  add(supplier: any) {
    return this.http.post<any>('http://localhost:8080/api/v1/supplier/add', supplier)
    .pipe(map(response => {
      return response;
    }))
  }

  lookup(text: string) {
    return this.http.get<any>(`http://localhost:8080/api/v1/supplier/search?text=${text}`)
    .pipe(map(response => {
        return response;
    }))

  }

  edit(supplier: any) {
    return this.http.put<any>('http://localhost:8080/api/v1/supplier/edit', supplier)
    .pipe(map(response => {
        console.log('Edit operation Response: ', response);
        return response;
    }))
  }

  all() {
    return this.http.get<any>('http://localhost:8080/api/v1/supplier/all')
    .pipe(map(response => {
        return response;
    }))
  }

}
