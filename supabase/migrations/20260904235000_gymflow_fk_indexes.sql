create index if not exists gf_payments_member_id_idx
  on public.gf_payments (member_id);

create index if not exists gf_progress_member_id_idx
  on public.gf_progress (member_id);

create index if not exists gf_routine_exercises_owner_id_idx
  on public.gf_routine_exercises (owner_id);

create index if not exists gf_routine_exercises_routine_id_idx
  on public.gf_routine_exercises (routine_id);

create index if not exists gf_routines_member_id_idx
  on public.gf_routines (member_id);
