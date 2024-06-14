import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getByPage(page: any) {
    return this.http.get<any>(`${environment.api}/product/products?page=${page}`)
    .pipe(map(response => {
        return response;
    }));
  }

  delete(productId: string) {
    return this.http.delete<any>(`${environment.api}/product/delete/${productId}`)
    .pipe(map(response => {
        console.log('delete operation result : ' + response)
        return response;
    }));
  }

  add(product: any) {
    return this.http.post<any>(`${environment.api}/product/add`, product)
    .pipe(map(response => {
      return response;
    }))
  }

  lookup(text: string) {
    return this.http.get<any>(`${environment.api}/product/search?text=${text}`)
    .pipe(map(response => {
        return response;
    }))

  }

  edit(product: any) {
    return this.http.put<any>(`${environment.api}/product/edit`, product)
    .pipe(map(response => {
        console.log('Edit operation Response: ', response);
        return response;
    }))
  }

  all() {
    return this.http.get<any>(`${environment.api}/product/all`)
    .pipe(map(response => {
        return response;
    }))
  }

}
