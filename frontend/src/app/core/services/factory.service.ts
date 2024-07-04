import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FactoryService {

  constructor(private http: HttpClient) {
        
  }

  delete(factoryId: string) {
    return this.http.delete<any>('http://localhost:8080/api/v1/factory/delete/' + factoryId)
    .pipe(map(response => {
        console.log('delete operation result : ' + response)
        return response;
    }));
  }

  getByPage(page: any) {
    return this.http.get<any>(`http://localhost:8080/api/v1/factory/factories?page=${page}`)
    .pipe(map(response => {
        return response;
    }));
  }
  
  add(factory: any) {
    return this.http.post<any>('http://localhost:8080/api/v1/factory/add', factory)
    .pipe(map(response => {
      return response;
    }))
  }

  edit(factory: any) {
    return this.http.put<any>('http://localhost:8080/api/v1/factory/edit', factory)
    .pipe(map(response => {
        console.log('Edit operation Response: ', response);
        return response;
    }))
}

  lookup(text: string) {
    return this.http.get<any>(`http://localhost:8080/api/v1/factory/search?text=${text}`)
    .pipe(map(response => {
        return response;
    }))
}

  











}
