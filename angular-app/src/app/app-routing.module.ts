import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NewUserComponent } from './components/new-user/new-user.component';
import { UsersAllComponent } from './components/users-all/users-all.component';
import { AuthGaurdService } from './services/auth-gaurd.service';

const routes: Routes = [
  {
    path: '',
    component: UsersAllComponent,
    canActivate: [AuthGaurdService],
    data: { roles: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'] },
  },
  {
    path: 'users',
    component: UsersAllComponent,
    canActivate: [AuthGaurdService],
    data: { roles: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'] },
  },
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthGaurdService],
  },
  {
    path: 'adduser',
    component: NewUserComponent,
    canActivate: [AuthGaurdService],
    data: { roles: ['ROLE_SUPERADMIN'] },
  },
  {
    path: 'edituser/:login',
    component: EditUserComponent,
    canActivate: [AuthGaurdService],
    data: { roles: ['ROLE_SUPERADMIN'] },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
