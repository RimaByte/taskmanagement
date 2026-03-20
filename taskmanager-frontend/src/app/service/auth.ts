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

  private logoutTimer: any = null;


  login(username: string, password: string, beiError: (nachricht: string) => void): void {
  this.http.post<LoginResponse>(
    "http://localhost:8080/auth/login",
    {username, password})
    .subscribe({
      next: antwort => {
        localStorage.setItem('token', antwort.token);
        this.logoutTimer = setTimeout(() => {
          this.logout();
        }, 120000);
        this.router.navigate(['/projects']);
      },
      error: () => {
        beiError("Falscher Benutzername oder Passwort");
      }
    });
}


  logout(): void{

    if (this.logoutTimer){
      clearTimeout(this.logoutTimer);
    }
    
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
    
  }

}
  
