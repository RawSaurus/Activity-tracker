/* tslint:disable */
/* eslint-disable */
import { TypeSuperclass } from '../models/type-superclass';
export interface AchievementResponseV2 {
  achievementId: number;
  biggestStreak?: number;
  currentStreak?: number;
  deadline?: string;
  finished?: boolean;
  info?: string;
  name?: string;
  type?: 'GOAL' | 'DAILY' | 'AMOUNT';
  typeData?: TypeSuperclass;
  unit?: string;
  xp?: number;
}
