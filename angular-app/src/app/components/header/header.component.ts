import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  firstName = '';
  lastName = '';

  constructor(public keycloakService: KeycloakService) {}

  ngOnInit(): void {
    this.keycloakService.loadUserProfile().then((user) => {
      if (user.firstName) {
        this.firstName = user.firstName;
      }
      if (user.lastName) {
        this.lastName = user.lastName;
      }
    });
  }
}
