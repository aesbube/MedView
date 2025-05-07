import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Router, RouterLink } from '@angular/router';
import { SearchesService } from '../searches.service';
import { Specialist } from '../../../models/specialist.model';
import { catchError, debounceTime, map, Observable, of, startWith, switchMap } from 'rxjs';

@Component({
  selector: 'app-equipment-search',
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
  templateUrl: './equipment-search.component.html',
  styleUrl: './equipment-search.component.css'
})
export class EquipmentSearchComponent {

  private searchesService = inject(SearchesService)
  searchControl1 = new FormControl('');
  equipments: Specialist[] = [];
  filteredSpecialists$: Observable<Specialist[]> | undefined;
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

    onOptionSelected(event: any) {
      const selectedUsername = event.option.value;
      this.router.navigate(['/specialist', selectedUsername]);
    }

    @Output() focused = new EventEmitter<boolean>();

    onFocus(): void {
      this.focused.emit(true);
    }

    onBlur(): void {
      this.focused.emit(false);
    }

}
