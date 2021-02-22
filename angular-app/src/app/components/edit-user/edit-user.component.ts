import { HttpErrorResponse } from '@angular/common/http';
import { stringify } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { User } from 'src/app/model/user';
import { ValidationError } from 'src/app/model/validation-error';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  heading = 'Edit User';
  editMode = true;
  user: User = new User();
  roles: string[];
  errors: ValidationError = new ValidationError();

  constructor(
    private usersService: UsersService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.getUser(params['login']);
    });
    this.getRoles();
  }

  getUser(login) {
    this.usersService.getUser(login).subscribe((data) => {
      this.user = data;
    });
  }

  getRoles() {
    this.usersService.getRoles().subscribe(
      (data) => {
        this.roles = data;
      },
      (error) => {
        console.error('Error. Cant get roles');
      }
    );
  }

  updateUser(): void {
    this.usersService.updateUser(this.user).subscribe(
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
