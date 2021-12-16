  del *.cf *.vcd
  ghdl -a    alu1_sub_ovf_behavioral.vhd
  ghdl -a tb_alu1_sub_ovf_behavioral.vhd
  ghdl -e    alu1sob
  ghdl -e tb_alu1sob
  ghdl -r tb_alu1sob --stop-time=200ns --disp-time --vcd=tb_alu1sob.vcd
  gtkwave tb_alu1sob.vcd
