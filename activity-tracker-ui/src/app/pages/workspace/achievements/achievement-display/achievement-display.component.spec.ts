import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AchievementDisplayComponent } from './achievement-display.component';

describe('AchievementDisplayComponent', () => {
  let component: AchievementDisplayComponent;
  let fixture: ComponentFixture<AchievementDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AchievementDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AchievementDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
