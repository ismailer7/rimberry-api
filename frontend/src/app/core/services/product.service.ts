import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getByPage(page: any) {
    return this.http.get<any>(`http://localhost:8080/api/v1/product/products?page=${page}`)
    .pipe(map(response => {
        return response;
    }));
  }

  getById(id: number) {
    return this.http.get<any>(`http://localhost:8080/api/v1/product?id=${id}`)
    .pipe(map(response => {
      return response;
    }))
  }

  delete(productId: string) {
    return this.http.delete<any>('http://localhost:8080/api/v1/product/delete/' + productId)
    .pipe(map(response => {
        console.log('delete operation result : ' + response)
        return response;
    }));
  }

  add(product: any) {
    return this.http.post<any>('http://localhost:8080/api/v1/product/add', product)
    .pipe(map(response => {
      return response;
    }))
  }

  lookup(text: string) {
    return this.http.get<any>(`http://localhost:8080/api/v1/product/search?text=${text}`)
    .pipe(map(response => {
        return response;
    }))

  }

  edit(product: any) {
    return this.http.put<any>('http://localhost:8080/api/v1/product/edit', product)
    .pipe(map(response => {
        console.log('Edit operation Response: ', response);
        return response;
    }))
  }

  all() {
    return this.http.get<any>('http://localhost:8080/api/v1/product/all')
    .pipe(map(response => {
        return response;
    }))
  }

}
