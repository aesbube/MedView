import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { EN } from '../translations/en';
import { MK } from '../translations/mk';

export type AppLang = 'mk' | 'en';

const LANG_KEY = 'app_lang';

const TRANSLATIONS: Record<AppLang, Record<string, string>> = {
  en: EN,
  mk: MK,
};

@Injectable({ providedIn: 'root' })
export class TranslationService {
  private langSubject: BehaviorSubject<AppLang>;
  lang$: Observable<AppLang>;

  constructor() {
    const stored = localStorage.getItem(LANG_KEY) as AppLang | null;
    const initial: AppLang = stored === 'en' ? 'en' : 'mk';
    this.langSubject = new BehaviorSubject<AppLang>(initial);
    this.lang$ = this.langSubject.asObservable();
  }

  get currentLang(): AppLang {
    return this.langSubject.value;
  }

  setLang(lang: AppLang): void {
    localStorage.setItem(LANG_KEY, lang);
    this.langSubject.next(lang);
  }

  translate(key: string): string {
    return TRANSLATIONS[this.currentLang][key] ?? key;
  }
}
