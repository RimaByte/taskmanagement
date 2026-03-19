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

constructor(private http: HttpClient ){}

private getHeaders(): HttpHeaders {
  return new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem('token')});
}

getProjects(): Observable<ProjectResponse[]>{

const headers = this.getHeaders();
return this.http.get<ProjectResponse[]>('http://localhost:8080/projects', {headers})
}

  createProject(name: string, description: string): Observable<any> {
  const headers = this.getHeaders();
    return this.http.post<any>(
      'http://localhost:8080/projects',
      {name, description},
      {headers}
    );
  }

  loescheProjekt(id: number): Observable<any>{
   const headers = this.getHeaders();
   return this.http.delete<any>(
      `http://localhost:8080/projects/${id}`,
      {headers}
    );
}
}
