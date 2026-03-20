import { inject } from '@angular/core';
import { Router } from '@angular/router';

export function authGuard(): boolean {
    const router = inject(Router);
    const token = localStorage.getItem('token');
    if (token){
        return true;
    }else {
        router.navigate(['/login']);
        return false;
    }
}