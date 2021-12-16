int print_int(int);
int print_str(char *);
int fun1(int), fun2(int);

char *str0 = "start fun 1";
char *str1 = "start fun 2";
char *str2 = "we should never get here";
int x = 10;
int v = 20;
int data = 9;
 
int main ()
 {
  v = fun1(x);
  print_int(v);
  if (v == 2) return(2);
  v = fun2(x);
  print_str(str2);
  return(1);
 }

int fun1 (int x)
 {
  print_str(str0);
  return(1);
 }

int fun2 (int x)
 {
  print_str(str1);
  return(2);
 }
