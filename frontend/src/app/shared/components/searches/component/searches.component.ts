import { Component, inject, ViewChild } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatAutocomplete, MatAutocompleteModule, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import { SearchesService } from '../../searches.service';
import { Specialist } from '../../../../models/specialist.model';
import { MatSelectModule } from '@angular/material/select';
import { catchError, debounceTime, map, Observable, of, startWith, Subject, switchMap } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-searches',
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatSelectModule,
    AsyncPipe,
    RouterLink
  ],
  templateUrl: './searches.component.html',
  styleUrl: './searches.component.css',
})
export class SearchesComponent {

  private searchesService = inject(SearchesService)

  searchControl1 = new FormControl('');
  searchControl2 = new FormControl('');
  specialistsOrEquipments: Specialist[] = [];
  filteredSpecialists$: Observable<Specialist[]> | undefined;

  private searchTerms = new Subject<string>();

  constructor(private router: Router) {}


  ngOnInit() {
    this.filteredSpecialists$ = this.searchControl1.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      switchMap(term => {
        if (!term || term.length < 2) {
          return of([]);
        }
        return this.searchesService.findDoctorOrEquipment(term).pipe(
          map(doctors => doctors ? doctors : []),
          catchError(err => {
            console.error('Error searching for doctors:', err);
            return of([]);
          })
        );
      })
    );
  }
  trackByFn(index: number, specialist: Specialist): any {
    return specialist.username;
  }

  goToAppointment(){
    this.router.navigate(['/appointment', this.searchControl2.value]);
  }

  onOptionSelected(event: any) {
    const selectedUsername = event.option.value;
    this.router.navigate(['/specialist', selectedUsername]);
  }


}
