import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Tasks } from './components/tasks/tasks';
import { Register } from './components/register/register';



export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'projects', component: Projects},
    {path: 'projects/:projectId/tasks', component: Tasks},
    {path: 'register', component: Register}

];
