import { TestBed } from '@angular/core/testing';

import { MatchStatisticsService } from './match-statistics.service';

describe('MatchStatisticsService', () => {
  let service: MatchStatisticsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchStatisticsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
