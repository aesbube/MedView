import { Pipe, PipeTransform, inject, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { TranslationService } from '../services/translation.service';
import { Subscription } from 'rxjs';

@Pipe({
  name: 't',
  standalone: true,
  pure: false,
})
export class TranslatePipe implements PipeTransform, OnDestroy {
  private ts = inject(TranslationService);
  private cdr = inject(ChangeDetectorRef);
  private sub: Subscription;

  constructor() {
    this.sub = this.ts.lang$.subscribe(() => this.cdr.markForCheck());
  }

  transform(key: string): string {
    return this.ts.translate(key);
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
