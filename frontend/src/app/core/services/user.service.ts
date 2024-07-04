import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map } from "rxjs/operators";


@Injectable({ providedIn: 'root' })
export class UserService {
    
    constructor(private http: HttpClient) {
        
    }

    getAll() {
        return this.http.get<any>('http://localhost:8080/api/v1/user/getAll')
        .pipe(map(response => {
            return response;
        }));
    }

    getByPage(page: any) {
        return this.http.get<any>(`http://localhost:8080/api/v1/users?page=${page}`)
            .pipe(map(response => {
                return response;
        }))
    }

    delete(userId: string) {
        return this.http.delete<any>('http://localhost:8080/api/v1/user/delete/' + userId)
        .pipe(map(response => {
            console.log('delete operation result : ' + response)
            return response;
        }));
    }

    add(user: any) {
        return this.http.post<any>('http://localhost:8080/api/v1/user/add', user)
        .pipe(map(response => {
            console.log('Add operation Result: ', response);
            return response;
        }));
    }

    edit(user: any) {
        return this.http.put<any>('http://localhost:8080/api/v1/user/edit', user)
        .pipe(map(response => {
            console.log('Edit operation Response: ', response);
            return response;
        }))
    }

    lookup(text: string) {
        return this.http.get<any>(`http://localhost:8080/api/v1/user/search?text=${text}`)
        .pipe(map(response => {
            return response;
        }))
    }

    logout(userId: string) {
        return this.http.get<any>('http://localhost:8080/api/v1/user/logout/' + userId)
        .pipe(map(response => {
            return response;
        }));
    }

}