INSERT INTO public.permission_groups (id, group_name)
SELECT 1, 'admin'
WHERE NOT EXISTS (SELECT 1 FROM public.permission_groups WHERE group_name = 'admin');

INSERT INTO public.permissions (id, permission_level, user_email, group_id)
SELECT 1, 'VIEW', 'admin@email.com', 1
WHERE NOT EXISTS (SELECT 1
                  FROM public.permissions
                  WHERE user_email = 'admin@email.com'
                    AND permission_level = 'VIEW'
                    AND group_id = 1);

INSERT INTO public.permissions (id, permission_level, user_email, group_id)
SELECT 2, 'EDIT', 'admin@email.com', 1
WHERE NOT EXISTS (SELECT 1
                  FROM public.permissions
                  WHERE user_email = 'admin@email.com'
                    AND permission_level = 'EDIT');
