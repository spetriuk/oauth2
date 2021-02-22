import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { User } from 'src/app/model/user';
import { AgeFormat } from 'src/app/helpers/ageformat';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-users-all',
  templateUrl: './users-all.component.html',
  styleUrls: ['./users-all.component.css'],
})
export class UsersAllComponent implements OnInit {
  users: User[];
  userRoles: string[];

  constructor(
    private usersService: UsersService,
    private keycloakService: KeycloakService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.readUsers();
    this.userRoles = this.keycloakService.getUserRoles();
  }

  readUsers(): void {
    this.usersService.readAll().subscribe(
      (data: User[]) => {
        this.users = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  deleteUser(login: string): void {
    this.usersService.deleteUser(login).subscribe(
      (data) => {
        this.readUsers();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  open(content, login: string) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (success) => {
          console.log(success + ' ' + login);
          this.deleteUser(login);
        },
        (cancel) => {
          console.log(cancel + ' ' + login);
        }
      );
  }
}
