import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { ValidationError } from 'src/app/model/validation-error';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  @Input() user: User;
  @Input('heading') heading: string;
  @Input('editMode') editMode: boolean;
  @Input() public process: Function;
  @Input('errors') errors: ValidationError;
  roles: string[];

  constructor(private usersService: UsersService, private router: Router) {}

  ngOnInit(): void {
    this.usersService.getRoles().subscribe(
      (data) => {
        this.roles = data;
      },
      (error) => {
        console.error('Error. Cant get roles', error);
      }
    );
  }
}
