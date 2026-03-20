import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Projects } from './components/projects/projects';
import { Tasks } from './components/tasks/tasks';
import { Register } from './components/register/register';
import { authGuard } from './guards/auth.guard';



export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'projects', component: Projects, canActivate: [authGuard]},
    {path: 'projects/:projectId/tasks', component: Tasks, canActivate: [authGuard]},
    {path: 'register', component: Register}

];
