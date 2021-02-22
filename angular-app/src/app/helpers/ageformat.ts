import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ageformat',
})
export class AgeFormat implements PipeTransform {
  transform(birthday: string): string {
    const today = new Date();
    const birthDate = new Date(birthday);
    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();

    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    return age.toString();
  }
}
