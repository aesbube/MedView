import {Component, inject, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {Specialist} from '../../../models/specialist.model';
import {SearchesService} from '../searches.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {filter, map} from 'rxjs/operators';
import {switchMap} from 'rxjs';
import {MatIconButton} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {TranslatePipe} from '../../../core/pipes/translate.pipe';

@Component({
  selector: 'app-specialist',
  imports: [
    DatePipe,
    MatProgressSpinner,
    MatIconModule,
    MatIconButton,
    RouterLink,
    TranslatePipe
  ],
  templateUrl: './specialist.component.html',
  styleUrl: './specialist.component.css'
})
export class SpecialistComponent implements OnInit {
  specialist?: Specialist;
  service = inject(SearchesService);
  route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(params => params.get('username')),
      filter((username): username is string => !!username),
      switchMap(username => this.service.findSpecialistByUsername(username))
    ).subscribe({
      next: specialist => this.specialist = specialist,
      error: err => console.error('Failed to load specialist:', err)
    });
  }
}
