import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface ProjectResponse{
  id: number
  name: string
  description: string
}

@Injectable({
  providedIn: 'root',
})
export class Project {

constructor(private http: HttpClient, ){}

getProjects(): Observable<ProjectResponse[]>{

const headers = new HttpHeaders({
  'Authorization': 'Bearer ' + localStorage.getItem('token')
});

return this.http.get<ProjectResponse[]>('http://localhost:8080/projects', {headers})
}



}
