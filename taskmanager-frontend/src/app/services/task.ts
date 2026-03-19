import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface TaskResponse {
  id: number;
  title: string;
  description: string;
  status: string;
  priority: string;
  dueDate: string;
  projectId: number;

}

@Injectable({
  providedIn: 'root',
})
export class Task {

  constructor(private http: HttpClient){}


private getHeaders(): HttpHeaders {
  return new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem('token')});
}

getTasks(projectId: number): Observable<TaskResponse[]>{
  const headers = this.getHeaders();
  return this.http.get<TaskResponse[]>(`http://localhost:8080/projects/${projectId}/tasks`, {headers});
}


createTask( title: string, description: string, status: string, priority: string, dueDate: string, projectId: number): Observable<any>{
  const headers = this.getHeaders();
  return this.http.post<any>(`http://localhost:8080/projects/${projectId}/tasks`,
  {title, description, status, priority, dueDate},
    {headers});
}

deleteTask(projectId: number, id: number): Observable<any>{
  const headers = this.getHeaders();
  return this.http.delete<any>(`http://localhost:8080/projects/${projectId}/tasks/${id}`, {headers});
}

}
