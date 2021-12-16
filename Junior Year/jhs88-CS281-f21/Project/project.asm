 .sdata
 .align 2
data:
        .word   9
v:
        .word   20
x:
        .word   10
$LC0:
        .ascii  "we should never get here\000"
str2:
        .word   $LC0
$LC1:
        .ascii  "start fun 2\000"
str1:
        .word   $LC1
$LC2:
        .ascii  "start fun 1\000"
str0:
        .word   $LC2
 .text
 .align 2
main:
        addiu   $sp,$sp,-40
        sw      $31,36($sp)
        sw      $17,32($sp)
        sw      $16,28($sp)
        la      $2,str0
        lw      $4,0($2)
        la      $16,v
        jal     print_str
        li      $2,1                        # 0x1
        li      $4,1                        # 0x1
        sw      $2,0($16)
        jal     print_int
        lw      $2,0($16)
        li      $17,2                 # 0x2
        beq     $2,$17,$L2
        la      $2,str1
        lw      $4,0($2)
        jal     print_str
        la      $2,str2
        lw      $4,0($2)
        sw      $17,0($16)
        jal     print_str
        li      $2,1                        # 0x1
$L2:
        lw      $31,36($sp)
        lw      $17,32($sp)
        lw      $16,28($sp)
        addiu   $sp,$sp,40
        j       $31
fun1:
        addiu   $sp,$sp,-32
        sw      $31,28($sp)
        la      $2,str0
        lw      $4,0($2)
        jal     print_str
        lw      $31,28($sp)
        li      $2,1                        # 0x1
        addiu   $sp,$sp,32
        j       $31
fun2:
        addiu   $sp,$sp,-32
        sw      $31,28($sp)
        la      $2,str1
        lw      $4,0($2)
        jal     print_str
        lw      $31,28($sp)
        li      $2,2                        # 0x2
        addiu   $sp,$sp,32
        j       $31
print_int:
        move    $2,$4
        li      $3,1
        add     $a0,$4,$zero
        add     $v0,$3,$zero
        syscall
        j       $31
print_str:
        li      $2,4
        add     $a0,$4,$zero
        add     $v0,$2,$zero
        syscall
        move    $2,$0
        j       $31

