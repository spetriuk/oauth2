import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { User } from 'src/app/model/user';
import { ValidationError } from 'src/app/model/validation-error';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css'],
})
export class NewUserComponent implements OnInit {
  heading = 'Add User';
  editMode = false;
  user: User = new User();
  roles: string[];
  errors: ValidationError = new ValidationError();

  constructor(private usersService: UsersService, private router: Router) {}

  ngOnInit(): void {}

  public createUser(): void {
    this.usersService.createUser(this.user).subscribe(
      (data) => {
        console.log('User created successfully.');
        this.router.navigate(['/']);
      },
      (error) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 422) {
            console.log('Server validation error');
            this.errors = JSON.parse(JSON.stringify(error.error));
            console.log('error body: ' + JSON.stringify(this.errors));
          }
        }
      }
    );
  }
}
