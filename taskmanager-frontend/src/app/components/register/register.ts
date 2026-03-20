import { HttpClient } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule, MatIconButton } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';


@Component({
  selector: 'app-register',
  imports: [FormsModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register{

  constructor(private http: HttpClient, private router: Router){}

  username: string = '';
  email: string = '';
  password: string = '';
  fehler: string='';




  register(): void{
    if (!this.username || !this.email || !this.password){
      this.fehler ="Bitte alle Felder ausfüllen";
    }else if (!this.email.includes('@')) {
      this.fehler ="Bitte eine gültige E-Mail eingeben";
    }else {
      this.http.post<any>('http://localhost:8080/register', {username: this.username, email: this.email, password: this.password})
   .subscribe({
  next: () => {
    this.router.navigate(['/login']);
  },
  error: () => {
    this.fehler = "Registrierung fehlgeschlagen. Benutzername oder E-Mail bereits vergeben.";
  }
})
    }
  }


  zuLogin(): void{
    this.router.navigate(['/login']);
  }

    hide = signal(true);
   clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }
}

