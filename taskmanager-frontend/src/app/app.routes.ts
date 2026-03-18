import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';


export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'projects', component: Projects}
    
];
