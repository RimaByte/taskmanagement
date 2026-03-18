import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})

export class Auth {


  constructor(private http: HttpClient, private router: Router){}


  login(username: string, password:string): void {
    this.http.post<LoginResponse>(
      "http://localhost:8080/auth/login",
      {username, password})
      .subscribe(antwort => {
      localStorage.setItem('token', antwort.token);
      this.router.navigate(['/projects']);
    });
  } 



}
  
