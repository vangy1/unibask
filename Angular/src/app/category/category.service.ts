import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Category} from "./category";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  getAllCategories() {

  }

  getLeafCategories() {
    return this.http.get<Category[]>(environment.apiUrl + '/api/categories?leaf=true', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  getCategories() {
    return this.http.get<Category[]>(environment.apiUrl + '/api/categories', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }

  favoriteCategory(id: number) {
    return this.http.post(environment.apiUrl + '/api/category/favorite', {
      'id': id,
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
    })
  }
}
