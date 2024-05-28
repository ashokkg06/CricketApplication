import { TestBed } from '@angular/core/testing';

import { MatchSquadService } from './match-squad.service';

describe('MatchSquadService', () => {
  let service: MatchSquadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchSquadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
