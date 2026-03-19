import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register{

  constructor(private http: HttpClient, private router: Router){}

  username: string = '';
  email: string = '';
  password: string = '';


  register(): void{
    if (!this.username || !this.email || !this.password){
      console.log("ungueltige Eingabe");
    }else {
      this.http.post<any>('http://localhost:8080/register', {username: this.username, email: this.email, password: this.password})
      .subscribe(() => {
        this.router.navigate(['/login']);
      })
    }
  }

}
